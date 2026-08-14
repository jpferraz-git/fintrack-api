import type { Meta, StoryObj } from '@storybook/angular';
import { SettingsIdentityCard } from './settings-identity-card.component';

const meta: Meta<SettingsIdentityCard> = {
  title: 'Components/SettingsIdentityCard',
  component: SettingsIdentityCard,
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<SettingsIdentityCard>;

export const Primary: Story = {
  args: {},
};
