import type { Meta, StoryObj } from '@storybook/angular';
import { SettingsPage } from './settings.component';

const meta: Meta<SettingsPage> = {
  title: 'Components/SettingsPage',
  component: SettingsPage,
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<SettingsPage>;

export const Primary: Story = {
  args: {},
};
