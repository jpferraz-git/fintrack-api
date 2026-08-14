import type { Meta, StoryObj } from '@storybook/angular';
import { SettingsSecurityCard } from './settings-security-card.component';

const meta: Meta<SettingsSecurityCard> = {
  title: 'Components/SettingsSecurityCard',
  component: SettingsSecurityCard,
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<SettingsSecurityCard>;

export const Primary: Story = {
  args: {},
};
