import type { Meta, StoryObj } from '@storybook/angular';
import { SettingsHeader } from './settings-header.component';

const meta: Meta<SettingsHeader> = {
  title: 'Components/SettingsHeader',
  component: SettingsHeader,
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<SettingsHeader>;

export const Primary: Story = {
  args: {},
};
